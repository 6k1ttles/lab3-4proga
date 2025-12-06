package logic;

/**
 * Запись для хранения результата действия
 * @param success Успешно ли завершилось действие
 * @param message Текстовое описание результата
 */
public record TryResult(boolean success, String message) {

}
