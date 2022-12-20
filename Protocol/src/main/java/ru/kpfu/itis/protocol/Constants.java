package ru.kpfu.itis.protocol;

public class Constants {
    public static final double VERSION = 0.1;
    public static final int PORT = 8080;

    //удалил всё что пока не надо, даём серверным ответам негативные значения, запросам позитивные :)

    //CLIENT
    public static final byte JOIN_ROOM = 1; // кидает юзер при попытке войти в комнату, вместе с типом идет имя юзера которое сохраниться у серва если место есть и юзеру дали войти
    public static final byte EXIT_ROOM = 2; // выход юзера, очищает его из списков и кидает список всем оставшимся, вместе передаётся Player, если вышедший юзер админ(по дефолту первый зашедший) то сервер даёт админку первому в новом списке и кидает ему месседж про админа
    public static final byte START_GAME = 3; // админ нажал старт (мб можно тут передавать список имен пользователей чтобы сервер их сравнил и выдал ошибку ес че но хз зачем тут у нач всё быстро происходит)
    public static final byte REDINESS = 4; //все готовы херня коммент кто готовы кто это отсылает вообще нисе не понятно

    //SERVER
    public static final byte ERROR = 0;
    public static final byte ALLOW_JOIN = -1; // позволяет войти если игроков вместе с новым юзером будет 8 или меньше, вместе идет экземпляр вошедшего Playera
    public static final byte DENY_JOIN = -2; // конект отвергается, вместе подаётся стринга с причиной
    public static final byte USERS_CHANGED = -3; // кидает новый список имен юзеров стрингой через запятую
    public static final byte GIVE_ADMIN_PERMISSION = -4; // даёт админку
    public static final byte GAME_STARTED = -5;  // успешное начало игры


}
