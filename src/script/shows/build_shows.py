import os
import sys
import collect
import builder
import database
import templates


def main(argv):
    template_path = argv[1].strip()
    build_path = f"{template_path}/shows/build"

    print("Connecting to database...")

    try:
        db = database.init_db_connection(
            user=os.environ.get("MYSQL_USER"),
            password=os.environ.get("MYSQL_PASSWORD"),
            ca_path=os.environ.get("MYSQL_CA_CERT"),
            cert_path=os.environ.get("MYSQL_CLIENT_CERT"),
            key_path=os.environ.get("MYSQL_CLIENT_KEY"),
        )
    except Exception as e:
        print("")
        sys.exit(e)

    if not db.is_connected():
        print("")
        sys.exit("Failed to connect to database.")

    print("Connected.\n")

    print("Collecting bands...")
    collect.collect_bands(db)

    print("Collectig venues...")
    collect.collect_venues(db)

    print("Constructing show data...")
    shows = collect.collect_shows(db)
    print("Show data constructed.\n")

    print("Closing database connection...")
    database.close_db_connection(db)
    print("Connection closed.\n")

    print("Reading templates...")

    try:
        year_template = templates.load_year_template(build_path)
        show_wrapper_template = templates.load_show_entry_template(build_path)
        venue_link_template = templates.load_venue_link_template(build_path)
        venue_maps_template = templates.load_venue_maps_template(build_path)
        show_performances_template = templates.load_show_band_schedule_template(
            build_path
        )
        show_extras_template = templates.load_show_extras_template(build_path)
        show_notes_template = templates.load_show_notes_template(build_path)
    except Exception as e:
        print("")
        sys.exit(e)

    print("Building HTML and writing to final...")
    shows_list = builder.build_shows(
        shows,
        year_template,
        show_wrapper_template,
        venue_link_template,
        venue_maps_template,
        show_performances_template,
        show_extras_template,
        show_notes_template,
    )

    contents = builder.build_contents(shows)

    try:
        templates.write_to_final(shows_list, contents, template_path)
    except Exception as e:
        print("")
        sys.exit(e)

    print("Done.")


if __name__ == "__main__":
    main(sys.argv)
