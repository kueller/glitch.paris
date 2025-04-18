from flask import Blueprint, send_file, url_for

import app


document = Blueprint("document", __name__)

ALLOWED_DOCUMENTS = ("versement_mobilites.pdf",)


@document.route("/<string:filename>", methods=["GET"])
def document_get(filename: str):
    if filename not in ALLOWED_DOCUMENTS:
        return "", 404
    url = url_for("static", filename=f"public/{filename}")
    return send_file(f"{app.config.ROOT_DIRECTORY}/{url}")
