#!/bin/bash

file="/tmp/cover-go.out"

 go test -coverprofile $file ./...
 go tool cover -html $file
