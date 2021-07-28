<img src="https://i.gyazo.com/fcbc3c4c7369d613fa7bfef1cae5ec3a.png" align="center" width="500" >

# FTAuth Android SDK 

## Особенности

- Авторизация и регистрация пользователей
- Возможность авторизации через Facebook, Google и Apple
- Настройка полей обязательных для регистрации
- Готовые классы UI объектов

***

## Первоначальная настройка SDK

Перед началом работы необходимо проинициализировать библиотеку. Лучше всего это делать в классе ***Application***.
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

**2. Подключение полей ввода**

Для подключения поля ввода нужно просто добавить его в **xml** файл разметки.

```xml
 <com.fruktorum.ftauth.customUI.auth.FTAuthEmailInputField
        android:id="@+id/email_input_field"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

**3. Особенности полей ввода элементов и примеры использования:**

Каждый ***InputField*** имеет публичные переменные ***inputField*** и ***description*** - метки, для ввода значений и для вывода ошибок валидации или ошибок с сервера соответсвенно. Их свойства можно переопределять, обращаясь к ним напрямую.

**Пример:**

```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailInputField.inputField.hint = "Hint"
        emailInputField.description.setTextColor(Color.BLACK) 
    }
```


