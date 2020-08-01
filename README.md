# PropertyConfig

## Usage:

config.properties file:
```java properties
user.login=admin
user.password=qwerty123
address=192.168.0.4
port=8080
```
Configuration interface:
```java
@Config("config.properties")
private interface ConnectionConfig {

    @Key("user.login")
    String getLogin();
    
    @Key("user.password")
    String getPassword();
    
    String address(); //If Key annotation is not present, method name is used instead
    
    String port();
}
```
Using configuration:
```java
ConnectionConfig config = ConfigFactory.createConfig(ConnectionConfig.class);
config.getLogin(); //admin
...
```
