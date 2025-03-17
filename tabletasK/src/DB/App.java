package DB;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        databasecheck db = new databasecheck();
        db.isConnection();


        try {
            db.DeleteFromTable("orders");
            db.DeleteFromTable("dishes");
            db.DeleteFromTable("categories");
            db.DeleteFromTable("customers");
            db.DeleteFromTable("waiters");
        } catch (SQLException e) {

        }


        db.CreateTable("categories", "category_id int PRIMARY KEY", "category_name VARCHAR(50)", "description VARCHAR(200)");
        db.CreateTable("dishes", "dish_id int PRIMARY KEY", "dish_name VARCHAR(100)", "price DECIMAL(10,2)", "category_id int REFERENCES categories (category_id)", "description VARCHAR(500)");
        db.CreateTable("customers", "customer_id int PRIMARY KEY", "name VARCHAR(100)", "phone VARCHAR(20)", "email VARCHAR(100)");
        db.CreateTable("waiters", "waiter_id int PRIMARY KEY", "name VARCHAR(100)", "phone VARCHAR(20)", "shift VARCHAR(20)");
        db.CreateTable("orders", "order_id int PRIMARY KEY", "customer_id int REFERENCES customers (customer_id)", "waiter_id int REFERENCES waiters (waiter_id)", "order_date TIMESTAMP", "total_amount DECIMAL(10,2)");


        db.InsertTable("categories", "1", "Основные блюда", "Горячие блюда из мяса, рыбы и птицы");
        db.InsertTable("categories", "2", "Супы", "Горячие и холодные супы");
        db.InsertTable("categories", "3", "Салаты", "Свежие и теплые салаты");
        db.InsertTable("categories", "4", "Десерты", "Сладкие блюда и выпечка");
        db.InsertTable("categories", "5", "Напитки", "Алкогольные и безалкогольные напитки");


        db.InsertTable5("dishes", "1", "Стейк Рибай", "2500.00", "1", "Сочный стейк из говяжьей вырезки");
        db.InsertTable5("dishes", "2", "Борщ", "450.00", "2", "Классический украинский борщ");
        db.InsertTable5("dishes", "3", "Цезарь", "550.00", "3", "Салат с курицей и соусом цезарь");
        db.InsertTable5("dishes", "4", "Тирамису", "650.00", "4", "Классический итальянский десерт");
        db.InsertTable5("dishes", "5", "Мохито", "450.00", "5", "Освежающий коктейль с мятой");


        db.InsertTable5("customers", "1", "Иван Петров", "+7 777 123 4567", "ivan@email.com");
        db.InsertTable5("customers", "2", "Анна Сидорова", "+7 777 765 4321", "anna@email.com");
        db.InsertTable5("customers", "3", "Михаил Иванов", "+7 777 111 2222", "mikhail@email.com");


        db.InsertTable5("waiters", "1", "Алексей Смирнов", "+7 777 333 4444", "Утренняя");
        db.InsertTable5("waiters", "2", "Елена Кузнецова", "+7 777 555 6666", "Вечерняя");
        db.InsertTable5("waiters", "3", "Дмитрий Попов", "+7 777 777 8888", "Ночная");


        db.InsertTable5("orders", "1", "1", "1", "2024-03-17 12:00:00", "2950.00");
        db.InsertTable5("orders", "2", "2", "2", "2024-03-17 13:30:00", "1500.00");
        db.InsertTable5("orders", "3", "3", "3", "2024-03-17 15:00:00", "1100.00");


        System.out.println("\nКатегории блюд:");
        db.SelectFromTable("categories");
        
        System.out.println("\nБлюда:");
        db.SelectFromTable("dishes");
        
        System.out.println("\nКлиенты:");
        db.SelectFromTable("customers");
        
        System.out.println("\nОфицианты:");
        db.SelectFromTable("waiters");
        
        System.out.println("\nЗаказы:");
        db.SelectFromTable("orders");


        System.out.println("\nПоиск блюд по категории 'Основные блюда':");
        db.SelectByCriteria("dishes", "category_id", "1");

        System.out.println("\nПоиск заказов клиента с ID 1:");
        db.SelectByCriteria("orders", "customer_id", "1");


        System.out.println("\nОбновление цены блюда:");
        db.UpdateRecord("dishes", "price", "2600.00", "dish_id", "1");
        System.out.println("Обновленная информация о блюде:");
        db.SelectByCriteria("dishes", "dish_id", "1");


        System.out.println("\nУдаление заказа с ID 3:");
        db.DeleteRecord("orders", "order_id", "3");
        System.out.println("Список заказов после удаления:");
        db.SelectFromTable("orders");
    }
}
