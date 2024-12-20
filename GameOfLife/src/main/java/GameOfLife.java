import java.util.Random;
import java.util.Scanner;

public class GameOfLife {

    private static final int DEAD = 0;
    private static final int ALIVE = 1;

    // Метод для инициализации поля случайными значениями
    public static int[][] initializeField(int rows, int cols) {
        int[][] field = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = random.nextInt(2); // Случайное значение 0 или 1
            }
        }

        return field;
    }

    // Метод для вывода состояния поля
    public static void printField(int[][] field) {
        for (int[] row : field) {
            for (int cell : row) {
                System.out.print(cell == ALIVE ? "O " : ". "); // Живая клетка - 'O', мертвая - '.'
            }
            System.out.println();
        }
    }

    // Метод для подсчета живых соседей клетки
    public static int countAliveNeighbors(int[][] field, int row, int col) {
        int aliveNeighbors = 0;
        int rows = field.length;
        int cols = field[0].length;

        // Проверяем все 8 соседей
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Пропускаем саму клетку

                int neighborRow = row + i;
                int neighborCol = col + j;

                // Проверяем, что сосед находится в пределах поля
                if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                    aliveNeighbors += field[neighborRow][neighborCol];
                }
            }
        }

        return aliveNeighbors;
    }

    // Метод для перехода к следующему поколению
    public static int[][] nextGeneration(int[][] field) {
        int rows = field.length;
        int cols = field[0].length;
        int[][] newField = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbors = countAliveNeighbors(field, i, j);

                // Применяем правила игры
                if (field[i][j] == ALIVE) {
                    // Живая клетка
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        newField[i][j] = DEAD; // Умерла
                    } else {
                        newField[i][j] = ALIVE; // Продолжает жить
                    }
                } else {
                    // Мёртвая клетка
                    if (aliveNeighbors == 3) {
                        newField[i][j] = ALIVE; // Родилась
                    } else {
                        newField[i][j] = DEAD; // Остаётся мёртвой
                    }
                }
            }
        }

        return newField;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введи размеры поля (строки и столбцы):");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        System.out.println("Введи количество поколений:");
        int generations = scanner.nextInt();

        // Инициализация поля
        int[][] field = initializeField(rows, cols);

        System.out.println("Начальное состояние поля:");
        printField(field);

        // Переход к следующим поколениям
        for (int gen = 1; gen <= generations; gen++) {
            field = nextGeneration(field);
            System.out.println("\nПоколение " + gen + ":");
            printField(field);
        }

        scanner.close();
    }
}
