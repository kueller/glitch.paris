# glitch.paris

Setup follows the DigitalOcean guide for Nginx and WSGI.
https://www.digitalocean.com/community/tutorials/how-to-serve-flask-applications-with-uwsgi-and-nginx-on-ubuntu-20-04

Change the path in [wsgi.py](https://github.com/kueller/glitch.paris/blob/main/wsgi.py) to be the directory this repository is installed on.
Also set this in the `WorkingDirectory` parameter in the systemd service.
