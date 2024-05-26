#!/usr/bin/env bash

curl -X POST http://localhost:8080/api/v1/convert/integers \
  -H "Content-Type: application/json" \
  -d '{"from": 1, "to": 5}'
