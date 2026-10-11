import mysql.connector
from mysql.connector import Error as DBError


def init_db_connection(
    user: str, password: str, ca_path: str, cert_path: str, key_path: str
):
    try:
        conn = mysql.connector.connect(
            host="glitch.paris",
            port="3306",
            user=user,
            password=password,
            database="glitch",
            ssl_ca=ca_path,
            ssl_cert=cert_path,
            ssl_key=key_path,
            ssl_verify_identity=True,
            ssl_disabled=False,
        )
    except DBError as e:
        raise (e)

    return conn


def close_db_connection(conn):
    if conn.is_connected():
        conn.close()


def fetch_bands(conn):
    cursor = conn.cursor()

    cursor.execute("""
    SELECT * FROM glitch.band
    """)

    results = cursor.fetchall()
    return results


def fetch_venues(conn):
    cursor = conn.cursor()

    cursor.execute("""
    SELECT * FROM glitch.venue
    """)

    results = cursor.fetchall()
    return results


def fetch_events(conn):
    cursor = conn.cursor()

    cursor.execute("""
    SELECT * FROM glitch.event
    ORDER BY date_start ASC
    """)

    results = cursor.fetchall()
    return results


def fetch_event_performances(conn, event_id):
    cursor = conn.cursor()

    cursor.execute(f"""
    SELECT * FROM glitch.band_event
    WHERE event_id={event_id}
    ORDER BY id ASC
    """)

    results = cursor.fetchall()
    return results
