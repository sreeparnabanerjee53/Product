#!/bin/bash -ex

mysql -h mysqltest -u root < /mysql.sql

exec "$@"

