# Animal Spring Service
for Art Plan

## По задаче
- при запуске происходит миграция тестовых данных в БД H2 - http://127.0.0.1:8190/test/h2-console/
- Swagger http://127.0.0.1:8190/test/swagger-ui.html


## Постановка задачи
Написать набор сервисов для SOA WEB приложения.
приложение должно реализовывать такие сервисы : 
- создание пользователЯ с (регистрация).
должен создаваться пользователь с именем и паролем.
имя должно быть уникальным.
сразу после созданиЯ текущий пользователь должен авторизоваться в том-же запросе.

- Не зарегистрированный пользователь должен иметь возможность проверить доступность имени через сервис (валидации).

- созданный в системе пользователь должен иметь возможность авторизоваться, передав в сервис имя и пароль.
Количество неудачных попыток авторизации - не должно превышать 10 за 1 час и сбрасываться при успешной авторизации.

авторизованный пользователь должен иметь возможность 
- создавать /редактировать/удалить животных [Вид(из списка-справочника), дата рождения, пол, Кличка(уникальна)] .
- получить список своих животных.
- получить детали любого животного по id.

Ввсе взаимодействие должно происходить с использованием JSON форамата данных.

Все ошибки должны иметь номера и текстовую расшифровки. 
ошибки, в случае возникновениЯ, так-же должны передаватьсЯ в виде JSON объекта.

Вв качестве базы данных можно взять PostgreSQL, Mongo или любую InMemory базу (но, тогда jar-ик надо добавить в архив).

Ррекомендуется использовать Spring и Hibernate (можно c JPA).