from flask import Blueprint, render_template

from app.night import generate_image_names

home = Blueprint("home", __name__)


@home.route("/")
def hello():
    return render_template("home.html")


@home.route("/night")
def night():
    images = generate_image_names()
    return render_template("night.html", images=images)
