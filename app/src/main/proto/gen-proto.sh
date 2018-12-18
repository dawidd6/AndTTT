#!/bin/bash

go_dir="../go/pkg/proto"
java_dir="../java"

protoc --go_out=${go_dir} *.proto
protoc --java_out=${java_dir} *.proto