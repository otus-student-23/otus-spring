### Тема "Онлайн-библиотека на микросервисной архитектуре с SSO-аутентификацией"

---
Проект является развитием "Онлайн-библиотеки" из ДЗ.
Приложение переведено на микросервисную архитектуру с SSO-аутентификацией и мониторингом средствами Prometheus.  
В качестве SSO-сервера использован Keycloak с дополнительно настроенной поддержкой аутентификации через внешнего провайдера Github.  
Добавлен микросервис нотификации, который позволяет по websocket отправлять push-сообщения в браузер залогиненным клиентам о происходящих событиях в библиотеке.
Например, при добавлении новой книги, залогиненные пользователи получат соответствующее уведомление.

---
### Состав приложения:

#### Сервера:
1. **Сервер БД** (*postgres*).
2. **config-server** (*spring-cloud-config-server*). Сервер конфигураций микросервисов приложения.
3. **gateway-server** (*spring-cloud-gateway, netty, spring-boot-starter-security, spring-boot-starter-oauth2-x*). Сервер маршрутизации и балансировки входящих внешних запросов к приложению.
4. **sso-server** (*keycloak, oidc/oauth2*). SSO-сервер с поддержкой внешних провайдеров (Github).
5. **Веб-сервер** (*nginx*). Веб-сервер для веб-клиента приложения.
6. **prometheus-server** (*prometheus*). Система мониторинга.

#### Микросервисы:
1. **booklibrary-microservice**. REST-сервис "Библиотека книг".
2. **auth-microservice**. REST-сервис авторизации, здесь ведутся роли учетных записей.
3. **notification-microservice** (spring-boot-starter-websocket). Сервис нотификации.

#### Клиенты:
1. **web-client** (*html, javascript/ajax, css*). Веб-клиент приложения с админкой.

#### Библиотеки, стартеры:
1. **notification-client** (*spring-cloud-starter-openfeign*). Openfeign-клиент к сервису нотификации.
2. **auth-starter** (*spring-boot-starter-oauth2-resource-server*). Подключаемый стартер авторизации для микросервисов приложения.

#### Развертывание
1. **docker-compose.yml**. Конфигурация для развертывания в docker-compose.
2. **k8s-config**. Конфигурация для развертывания в *kubernetes* (*minikube*).

### Задействованные технологии/протоколы/инструменты/системы:
```
Java, Spring, JUnit, HTTP/HTTPS, REST, JSON, WebSocket, Tomcat, Nginx, Netty,
SQL, PL/pgSQL, JDBC, JPA, Liquibase, Postgres, H2, OAuth2.0, OIDC, JWT,
Eureka, Keycloak, Prometheus, Docker, docker-compose, Kubernetes (minikube),
OpenApi, Swagger, HTML, JavaScript/AJAX, CSS, openssl, bash,
Maven, Git, Intellij IDEA.
```

### Ссылки приложения:
1. [Онлайн-библиотека, главная страница](http://localhost:8081/)
2. [Сервер конфигураций](http://localhost:8888/booklibrary-microservice/default)
3. [Eureka](http://localhost:8761/)
4. [Keycloak](http://localhost:8080/)
5. [Swagger](http://localhost:8081/swagger-ui/index.html)
6. [Prometheus](http://localhost:9090/)
7. [Grafana](http://localhost:3000/)
