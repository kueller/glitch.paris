def read_file(filename: str) -> str:
    with open(filename, "r") as f:
        return f.read().strip()


def _load_template(build_path: str, template_filename: str) -> str:
    return read_file(f"{build_path}/{template_filename}")


def _load_full_show_template(build_path: str) -> str:
    return _load_template(build_path, "shows-template.html")


def load_year_template(build_path: str) -> str:
    return _load_template(build_path, "year.html")


def load_show_entry_template(build_path: str) -> str:
    return _load_template(build_path, "show-main.html")


def load_venue_link_template(build_path: str) -> str:
    return _load_template(build_path, "venue-link.html")


def load_venue_maps_template(build_path: str) -> str:
    return _load_template(build_path, "venue-maps.html")


def load_show_band_schedule_template(build_path: str) -> str:
    return _load_template(build_path, "show-schedule.html")


def load_show_extras_template(build_path: str) -> str:
    return _load_template(build_path, "extra.html")


def load_show_notes_template(build_path: str) -> str:
    return _load_template(build_path, "notes.html")


def _indent_lines(text: str, shift: int) -> list:
    indenting_tokens = (
        "<div>",
        "<div",
        "<span>",
        "<span",
        "<a",
        "<svg",
        "<defs>",
        "<clipPath",
        "<filter",
        "<feComponentTransfer>",
        "<radialGradient",
    )

    returning_tokens = (
        "</div>",
        "</span>",
        "</a>",
        "</svg>",
        "</defs>",
        "</clipPath>",
        "</filter>",
        "</feComponentTransfer>",
        "</radialGradient>",
    )

    new_lines = []
    indent_factor = 0
    for line in text.split("\n"):
        clean = line.strip()
        if not clean:
            new_lines.append(clean)
            continue

        start = clean.split(" ")[0]

        if start in indenting_tokens and not any(
            [clean.endswith(tok) for tok in returning_tokens + ("/>", "</a>,")]
        ):
            new_lines.append(f"{' ' * (4 * indent_factor + shift)}{clean}")
            indent_factor += 1
        elif any([clean.endswith(tok) for tok in returning_tokens]) and not any(
            [clean.startswith(tok) for tok in indenting_tokens]
        ):
            indent_factor -= 1
            new_lines.append(f"{' ' * (4 * indent_factor + shift)}{clean}")
        else:
            new_lines.append(f"{' ' * (4 * indent_factor + shift)}{clean}")

    return new_lines


def write_to_final(shows: str, contents: str, template_path: str):
    full_template = _load_full_show_template(f"{template_path}/shows/build")
    final = []
    for line in full_template.split("\n"):
        if not "{contents}" in line and not "{shows}" in line:
            final.append(line)
        else:
            if "{contents}" in line:
                shift = line.find("{contents}")
                final += _indent_lines(contents, shift)
            if "{shows}" in line:
                shift = line.find("{shows}")
                final += _indent_lines(shows, shift)

    with open(f"{template_path}/shows/shows.html", "w") as f:
        f.write("\n".join(final))
