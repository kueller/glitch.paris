from flask import Blueprint, redirect

bsky = Blueprint("bsky", __name__)


@bsky.route("/")
def redirect_bluesky():
    return redirect("https://bsky.app/profile/kue.glitch.paris")