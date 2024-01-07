FROM redis:latest
ENV TIME_ZOME Asia/Shanghai
COPY redis.conf /usr/local/etc/redis/redis.conf
RUN chmod 777 /usr/local/etc/redis/redis.conf
EXPOSE 6379
CMD ["/usr/local/bin/redis-server"]
# CMD ["/usr/local/bin/redis-server","/usr/local/etc/redis/redis.conf"]