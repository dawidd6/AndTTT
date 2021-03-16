FROM golang:1-alpine as builder
ENV CGO_ENABLED=0
COPY . /app
WORKDIR /app
RUN go build -o andttt

FROM alpine:3 as runner
COPY --from=builder /app/andttt /bin/andttt
EXPOSE 33333/tcp
EXPOSE 33334/tcp
ENTRYPOINT ["andttt", "-log-messages", "-log-date"]
