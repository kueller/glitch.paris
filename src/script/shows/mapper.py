from enum import IntEnum, auto

BANDS = {}


VENUES = {}


class EventRow(IntEnum):
    EVENT_ID = 0
    EVENT_UUID = auto()
    EVENT_TYPE = auto()
    START_DATE = auto()
    END_DATE = auto()
    START_DATETIME = auto()
    TIMEZONE = auto()
    EVENT_VENUE_ID = auto()
    EVENT_NAME = auto()
    COMMENTS = auto()
    URL = auto()
    IMAGE_FILENAME = auto()


class BandRow(IntEnum):
    BAND_ID = 0
    BAND_UUID = auto()
    BAND_NAME = auto()
    BAND_LASTFM = auto()


class VenueRow(IntEnum):
    VENUE_ID = 0
    VENUE_UUID = auto()
    VENUE_NAME = auto()
    ADDRESS = auto()
    CITY = auto()
    POSTCODE = auto()
    STATE = auto()
    COUNTRY = auto()
    GOOGLE_URL = auto()
    VENUE_URL = auto()


class PerformanceRow(IntEnum):
    BAND_EVENT_ID = 0
    BAND_ID = auto()
    EVENT_ID = auto()
    HEADLINER = auto()
    PERFORMANCE_DATE = auto()


class Show:
    def __init__(self, row: tuple):
        self.id = row[EventRow.EVENT_ID]
        self.event_type = row[EventRow.EVENT_TYPE]
        self.name = row[EventRow.EVENT_NAME] if row[EventRow.EVENT_NAME] else None
        self.start_date = row[EventRow.START_DATE]
        self.end_date = row[EventRow.END_DATE] if row[EventRow.END_DATE] else None
        self.start_time = (
            row[EventRow.START_DATETIME] if row[EventRow.START_DATETIME] else None
        )
        self.comments = row[EventRow.COMMENTS] if row[EventRow.COMMENTS] else None
        self.image = (
            row[EventRow.IMAGE_FILENAME] if row[EventRow.IMAGE_FILENAME] else None
        )
        self.url = row[EventRow.URL] if row[EventRow.URL] else None

        self.venue = VENUES[row[EventRow.EVENT_VENUE_ID]]

        self.performances = {}


class Band:
    def __init__(self, row: tuple):
        self.id = row[BandRow.BAND_ID]
        self.uuid = row[BandRow.BAND_UUID]
        self.name = row[BandRow.BAND_NAME]
        self.lastfm = row[BandRow.BAND_LASTFM]


class Venue:
    def __init__(self, row: tuple):
        self.id = row[VenueRow.VENUE_ID]
        self.uuid = row[VenueRow.VENUE_UUID]
        self.name = row[VenueRow.VENUE_NAME]
        self.city = row[VenueRow.CITY]
        self.country = row[VenueRow.COUNTRY]
        self.google_url = row[VenueRow.GOOGLE_URL]
        self.url = row[VenueRow.VENUE_URL] if row[VenueRow.VENUE_URL] else None


class Performance:
    def __init__(self, row: tuple):
        self.id = row[PerformanceRow.BAND_EVENT_ID]
        self.band = BANDS[row[PerformanceRow.BAND_ID]]
        self.headliner = row[PerformanceRow.HEADLINER] == 1
        self.performance_date = row[PerformanceRow.PERFORMANCE_DATE]
