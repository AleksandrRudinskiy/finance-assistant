import java.util.ArrayList;
import java.util.HashMap;

public class ExpensesManager {
    HashMap<String, ArrayList<Double>> expensesByCategories;

    ExpensesManager() {
        expensesByCategories = new HashMap<>();
    }

    double saveExpense(double moneyBeforeSalary, String category, double expense) {
        moneyBeforeSalary = moneyBeforeSalary - expense;
        System.out.println("Значение сохранено! Ваш текущий баланс в рублях: " + moneyBeforeSalary);
        if (expensesByCategories.containsKey(category)) {
            ArrayList<Double> expenses = expensesByCategories.get(category);
            expenses.add(expense);
        } else {
            ArrayList<Double> expenses = new ArrayList<>();
            expenses.add(expense);
            expensesByCategories.put(category, expenses);
        }
        if (moneyBeforeSalary < 1000) {
            System.out.println("На вашем счету осталось совсем немного. Стоит начать экономить!");
        }
        return moneyBeforeSalary;
    }

    void printAllExpensesByCategories() {
        for (String category : expensesByCategories.keySet()) {
            System.out.println(category);
            ArrayList<Double> expenses = expensesByCategories.get(category);
            for (Double expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    double findMaxExpenseInCategory(String category) {
        double maxExpense = 0;
        if (expensesByCategories.containsKey(category)) {
            ArrayList<Double> expenses = expensesByCategories.get(category);
            for (Double expense : expenses) {
                if (expense > maxExpense) {
                    maxExpense = expense;
                }
            }
        } else {
            System.out.println("Такой категории пока нет.");
        }
        return maxExpense;
    }

    void removeAllExpenses() {
        expensesByCategories.clear();
        System.out.println("Траты удалены.");
    }

    double getExpensesSum(){// Напишите метод для получения суммы всех трат
        double sumRez  =0.0;

        for (String category : expensesByCategories.keySet()){
            double sum = 0.0;
            for (Double expense : expensesByCategories.get(category)){
                sum += expense;
            }
            sumRez += sum;
        }

        return sumRez;
    }

    void removeCategory(String category){// Напишите метод для удаления категории
        expensesByCategories.remove(category);
    }

    String getMaxCategoryName() { // Напишите метод для получения категории, где размер трат больше всего
        // Используйте эти переменные для сохранения промежуточных значений
        double maxCategorySum = 0;
        String maxCategoryName = "";

        for (String category : expensesByCategories.keySet()){
            double sum = 0.0;
            for (Double expense : expensesByCategories.get(category)){
                sum += expense;
            }
            if (sum>maxCategorySum) {
                maxCategorySum=sum;
                maxCategoryName = category;
            }

        }
        return maxCategoryName;
    }
}
