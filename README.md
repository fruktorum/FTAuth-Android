<img src="https://i.gyazo.com/fcbc3c4c7369d613fa7bfef1cae5ec3a.png" align="center" width="500" >

# FTAuth Android SDK 

## Особенности

- Авторизация и регистрация пользователей
- Возможность авторизации через Facebook, Google и Apple
- Настройка полей обязательных для регистрации
- Готовые классы UI объектов

***

## Первоначальная настройка SDK

Перед началом работы необходимо проинициализировать библиотеку. Лучше всего это делать в классе Application.
С помощью метода ***setServerUrl*** нужно установить URL для обращения к серверу.

```kotlin
class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FTAuth.Companion.Builder(this).setServerUrl("Г").build()
    }
}
```

## Подключение UI элементов

### InputField

**1. Описание полей:**

| Тип InputField | Описание |
| ------ | ------ |
| **Авторизация** ||
| FTAuthEmailInputField | Для ввода **email** при авторизации |
| FTAuthPasswordInputField | Для ввода **пароля** при авторизации | 
| **Регистрация** ||
| FTRegistrationNameInputField | Для ввода **имени в произвольном формате** (в одну строку) при регистрации |
| FTRegistrationFirstNameInputField | Для ввода **имени** при регистрации |
| FTRegistrationLastNameInputField | Для ввода **фамилии** при регистрации |
| FTRegistrationEmailInputField | Для ввода **email** при регистрации |
| FTRegistrationPasswordInputField | Для ввода **пароля** при регистрации |
| FTRegistrationConfirmPasswordInputField | Для **подтверждения пароля** при регистрации |
| FTRegistrationPhoneNumberInputField | Для ввода **номера телефона** при регистрации |