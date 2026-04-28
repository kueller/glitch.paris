# glitch.paris

## Build

Created with http4k using Thymeleaf for HTML templates. Build with 
```sh
mvn clean install
```

or skip the auto-minifying scripts with
```sh
mvn clean install -DskipMinify
```

## Optional requirements:

To minify the scripts during build. Using npm:
```sh
npm install -g lightningcss
npm install -g rollup
npm install -g terser
```

## Run

Set up environment variables. See [env.example](env.example).
The `env` or `.env` file is not used by the application. Load the
variables into the environment first and then run.

Run with
```sh
java -jar target/glitch-1.x.x.jar 
```
