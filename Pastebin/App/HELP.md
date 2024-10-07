# Запуск проекта
Перейдите в папку ./src/main/resources в ней создайте .env файл и укажите имя бакета и 
ключи для доступа к нему (автор использовал Yandex Cloud) шаблон данных:
```.dotenv
YANDEX_CLOUD_BUCKET=
YANDEX_CLOUD_ACCESS_KEY=
YANDEX_CLOUD_SECRET_KEY=
```
В корневой папке выполнить комманду:
```mvn
mvn clean install
```
Либо же данной коммандой для запуска без тестов
```mvn
mvn clean install -DskipTests
```
После успешной сборки проекта собрать через docker compose
```docker-compose
docker-compose up --build
```
Для доступа к endpoints перейти на страницу с документацией
```link
http://localhost:8080/swagger-ui/index.html
```
