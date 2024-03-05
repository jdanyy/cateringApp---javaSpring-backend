# Catering App backend with Spring Boot

## Database

- in `prod` profile there is a **PostgreSQL** database. The database **dockerfile** can be found at `db/Dockerfile`.

## Building

- you can build the project from the root directory
- just run the `docker compose build` command

## Running

- ❗Ensure that you are in the root directory
- `docker compose -f db/docker-compose.yml -f jdim2141-spring/docker-compose.prod.yml up`

🌟HELP🌟

- An useful tool may be the `lazydocker` which helps enorme to build, run, and handle multicontainerized applications

## Profiles

- there are two separate profiles inside the application:
  `dev` - with a **h2** database
  `prod` - with a **postgresql** database

- the profiles can be changed by setting the `SPRING_PROFILES_ACTIVE` environment variable, if this not set, the default value is `dev`

## Testing

- in root folder of the project, there is a postman collection for testing the API endpoints of the project
