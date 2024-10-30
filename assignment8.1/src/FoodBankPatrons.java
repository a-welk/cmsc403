public class FoodBankPatrons {

    public static void main(String[] args){
        FoodBank bank = new FoodBank();
        FoodProducer producer = new FoodProducer(bank);
        FoodConsumer consumer = new FoodConsumer(bank);
        producer.start();
        consumer.start();
    }
}