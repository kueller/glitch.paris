import markdown
import datetime as dt
from mapper import Show, Band, Venue, Performance

DAYS = ("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")


MONTHS = (
    "",
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
)


def _build_show_notes(show: Show, show_notes_template: str) -> str:
    if not show.comments:
        return ""

    formatted = markdown.markdown(show.comments).replace("<p>", "").replace("</p>", "")

    return "\n" + show_notes_template.format(comments=formatted)


def _build_show_extras(show: Show, show_extras_template: str) -> str:
    if not show.url and not show.image:
        return ""

    link = f'[<a target="_blank" href="{show.url}">link</a>]' if show.url else ""
    poster = f'[<a href="/static/shows/{show.image}">poster</a>]' if show.image else ""

    return "\n" + show_extras_template.format(link=link, poster=poster)


def _build_show_performances(show: Show, show_performances_template: str) -> str:
    if show.event_type == "CONCERT" and show.start_time:
        time = f"{show.start_time.hour:02d}:{show.start_time.minute:02d}"
    else:
        time = ""

    performance_list = []
    for performance_date in show.performances:
        day_of_week = DAYS[performance_date.weekday()]
        month = MONTHS[performance_date.month]

        performances = show.performances[performance_date]

        band_list = []
        for performance in performances:
            if performance.headliner:
                band = f"<em>{performance.band.name}</em>"
            else:
                band = performance.band.name

            band_list.append(
                f'<a href="./shows/band/{performance.band.uuid}">{band}</a>'
            )

        bands = ",\n".join(band_list)

        performance_list.append(
            show_performances_template.format(
                day_of_week=day_of_week,
                day=performance_date.day,
                month=month,
                year=performance_date.year,
                time=time,
                bands=bands,
            )
        )

    return "<br>\n".join(performance_list)


def _build_venue_maps(venue: Venue, venue_maps_template: str) -> str:
    return "\n" + venue_maps_template.format(link=venue.google_url, venueid=venue.uuid)


def _build_venue_link(venue: Venue, venue_links_template: str) -> str:
    if not venue.url:
        return ""

    return "\n" + venue_links_template.format(link=venue.url, venueid=venue.uuid)


def _build_shows_in_year(
    shows: list,
    show_wrapper_template: str,
    venue_links_template: str,
    venue_maps_template: str,
    show_performances_template: str,
    show_extras_template: str,
    show_notes_template: str,
) -> str:
    shows_formatted = []

    for show in shows:
        # Show name or first headliner
        show_name = (
            show.name
            if show.name
            else show.performances[list(show.performances.keys())[0]][0].band.name
        )

        venue_link = _build_venue_link(show.venue, venue_links_template)
        venue_maps = _build_venue_maps(show.venue, venue_maps_template)

        performances = _build_show_performances(show, show_performances_template)
        extras = _build_show_extras(show, show_extras_template)
        notes = _build_show_notes(show, show_notes_template)

        shows_formatted.append(
            show_wrapper_template.format(
                title=show_name,
                venue=show.venue.name,
                venue_link=venue_link,
                venue_maps=venue_maps,
                city=show.venue.city,
                country=show.venue.country,
                schedule=performances,
                extra=extras,
                notes=notes,
            )
        )

    return "\n<h2></h2>\n".join(shows_formatted)


def _get_years(shows: list) -> list:
    years_unique = set([show.start_date.year for show in shows])
    years = list(years_unique)
    years.sort()
    return years


def build_shows(
    shows: list,
    year_template: str,
    show_wrapper_template: str,
    venue_links_template: str,
    venue_maps_template: str,
    show_performances_template: str,
    show_extras_template: str,
    show_notes_template: str,
) -> str:
    years = _get_years(shows)

    years_formatted = []
    for year in years:
        shows_in_year = [show for show in shows if show.start_date.year == year]
        shows_data = _build_shows_in_year(
            shows_in_year,
            show_wrapper_template,
            venue_links_template,
            venue_maps_template,
            show_performances_template,
            show_extras_template,
            show_notes_template,
        )

        years_formatted.append(year_template.format(year=year, shows=shows_data))

    return "\n\n".join(years_formatted)


def build_contents(shows: list) -> str:
    years = _get_years(shows)

    tokens = []
    prev_mod = 0
    for year in years:
        mod = year % 10
        if mod < prev_mod:
            tokens.append("<br>")
        tokens.append(f'<a href="#{year}">{year}</a>')
        prev_mod = mod

    return "\n".join(tokens)
