# StudySprint

StudySprint is an innovative mobile application whose role is to help your
productivity using the Pomodoro technique at work. It also enables
synchronization of work sessions between multiple users in real time.

This is the backend for the application built in Java Spring, utilizing a PostgreSQL Database.

If you'd like to access and read the documentation for the frontend you can find it <a href="https://github.com/martintrifunov/studysprint">here.</a>

## Design Patterns for the Backend

- Singleton connection to Database
- Automatic Custom State Management on the Backend itself
- Time and Context synchronization between Frontend and Backend

## Setup Guide

### Make sure to install Java

You need Docker for this project (Or a PostgreSQL Server binded to the Spring project)

Run the commands:
```bash
 docker compose build
 docker compose up
```

When the project starts, check the port in Docker Desktop and use that on your frontend.
