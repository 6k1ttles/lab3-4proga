/**
 * Запись для хранения результата попытки действия
 *
 * @param success Успешно ли завершилось действие
 * @param message Текстовое описание результата
 */
public record TryResult(boolean success, String message) {

}
