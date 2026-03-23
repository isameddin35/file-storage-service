# File Storage Service

A secure file storage and management service built with **Spring Boot**, **PostgreSQL**, and **MinIO**, supporting automatic developer registration, per-user API keys, and audit logging.

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup & Installation](#setup--installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Audit Logs](#audit-logs)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **File Storage** – Upload, download, and manage files.
- **Automatic Developer Registration** – Users are registered automatically without manual intervention.
- **Per-User API Keys** – Each developer has their own API key, avoiding conflicts between multiple users.
- **Audit Logging** – Tracks file actions, API key generation, and other critical operations.
- **Database Migrations** – Managed with **Liquibase**.
- **Docker Support** – Easy deployment with **Docker Compose**.

---

## Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **File Storage:** MinIO
- **Database Migrations:** Liquibase
- **Security:** API Key-based authentication

---

## Setup & Installation

### Prerequisites

- Java 17+
- Docker & Docker Compose
- PostgreSQL
- MinIO (or any S3-compatible storage)

### Running with Docker Compose

```bash
git clone <your-repo-url>
cd file-storage-service
docker-compose up -d
