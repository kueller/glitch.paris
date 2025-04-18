from flask import Flask
from flask_login import LoginManager
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy

from app.config import APP_SECRET_KEY

app = Flask(__name__)
app.url_map.strict_slashes = False

app.jinja_env.trim_blocks = True
app.jinja_env.lstrip_blocks = True

app.secret_key = APP_SECRET_KEY

from app.home import home
from app.document.document import document

app.register_blueprint(home)
app.register_blueprint(document, url_prefix="/documents")

if __name__ == "__main__":
    app.run(host="0.0.0.0")
