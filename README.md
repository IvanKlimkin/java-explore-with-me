# java-explore-with-me

Repository for ExploreWithMe project.

https://github.com/IvanKlimkin/java-explore-with-me/pull/1

Проект "Explore with me" позволяет добавлять различные события, организовать встречу и устроить турнир по настольным
играм с ограниченным кругом участвников или же прогулка по живописным местам для всех желающих - все необходимое 
представлено в этом сервиме.

Проект включает два сервиса:
1.Основной сервис - содержит все данные по пользователям, событиям и категориям мероприятий.
2.Сервис статистики - содержит данные по просмотрам событий, позволяя предоставлять пользователям наиболее интересные 
мероприяти или подборки событий.

Проект построен на Spting Boot с основными зависимостями для удобства работы:
- spring-boot-starter-web;
- spring-boot-starter-data-jpa;
- spring-boot-starter-actuator;
База данных - Postgres с инициализацией скриптом в schema.sql, настройка не встроенной БД по директиве в application 
properties: "spring.sql.init.mode=always", для целей тестирования используется встроенная H2.

Для локального запуска основного сервиса **ewm-service** необходимо указать переменные среды в application.properties:
- SPRING_DATASOURCE_URL;
- SPRING_DATASOURCE_USERNAME;
- SPRING_DATASOURCE_PASSWORD;
Значения использованные при разработке приложения:
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ewmservice;
SPRING_DATASOURCE_USERNAME=ewmservice;
SPRING_DATASOURCE_PASSWORD=ewmservice;

Для локального запуска сервиса статистики **stat-service** необходимо указать переменные среды в application.properties:
- SPRING_DATASOURCE_URL;
- SPRING_DATASOURCE_USERNAME;
- SPRING_DATASOURCE_PASSWORD;
  Значения использованные при разработке приложения:
  SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ewmstats;
  SPRING_DATASOURCE_USERNAME=ewmstats;
  SPRING_DATASOURCE_PASSWORD=ewmstats;

Для запуска приложения **Explore with me** необходимо указать переменные среды в обоих файлах.
Для запуска приложения используя Docker Compose необходимо запустить командой:"docker-compose-up", в результате 
выполнения инструкций развернется проект Explore with me с базой данных Postgres(настройки проекта, включая переменные 
окружения в скрипте:"docker-compose.yml"), предварительно необходимо сгенерировать .jar файлы запуском команды: mvn
clean package.

