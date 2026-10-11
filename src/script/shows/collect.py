import database
from mapper import *


def collect_bands(db):
    results = database.fetch_bands(db)
    for row in results:
        band = Band(row)
        BANDS[band.id] = band


def collect_venues(db):
    results = database.fetch_venues(db)
    for row in results:
        venue = Venue(row)
        VENUES[venue.id] = venue


def collect_shows(db) -> list:
    results = database.fetch_events(db)

    shows = []
    for row in results:
        show = Show(row)
        performances = [
            Performance(p) for p in database.fetch_event_performances(db, show.id)
        ]
        for performance in performances:
            if performance.performance_date not in show.performances:
                show.performances[performance.performance_date] = []
            show.performances[performance.performance_date].append(performance)
        shows.append(show)

    return shows
