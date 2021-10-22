package hibernateORM;

import model.Basket;
import model.ClothesOnSale;
import model.UsersIntShop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class InternetShopORM {

    private static SessionFactory sessionFactory;

    public InternetShopORM() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void addClothesOnSale(String type, String size, String color, int cost) { // Добавить одежду на продажу
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        ClothesOnSale clothe = new ClothesOnSale(type, size, color, cost);

        session.save(clothe);
        transaction.commit();
        session.close();
        System.out.println("Предмет одежды успешно выставлен на продажу!");
    }

    public void updateСostClothes(int id, int cost) { // Обновить цену на предмет одежды
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        ClothesOnSale clothe = (ClothesOnSale) session.get(ClothesOnSale.class, id);
        clothe.setCost(cost);
        session.update(clothe);
        transaction.commit();
        session.close();
        System.out.println("Стоимость предмета одежды успешно обновлена!");
    }

    public void removeClothes(int id) { // Удалить предмет одежды
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            ClothesOnSale clothe = (ClothesOnSale) session.get(ClothesOnSale.class, id);
            session.delete(clothe);
            transaction.commit();
            session.close();
            System.out.println("Предмет одежды успешно убран с продажи!");
        } catch (Exception e) {
            System.out.println("Произошла ошибка, проверьте правильность введённых данных!");
        }
    }

    public void showUsersIntShop() { // Показать всех пользователей
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<UsersIntShop> users = session.createQuery("FROM model.UsersIntShop").list();
        for (UsersIntShop user : users) {
            System.out.println(user);
        }

        transaction.commit();
        session.close();
    }

    public void showClothesOnSale() { // Показать всю одежду на продажу
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<ClothesOnSale> clothes = session.createQuery("FROM model.ClothesOnSale").list();
        for (ClothesOnSale clothe : clothes) {
            System.out.println(clothe);
        }

        transaction.commit();
        session.close();
    }

    public void showBasket() { // Показать корзины всех пользователей
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<Basket> baskets = session.createQuery("FROM model.Basket").list();
        for (Basket basket : baskets) {
            System.out.println(basket);
        }

        transaction.commit();
        session.close();
    }
}
