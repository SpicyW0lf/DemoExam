package ru.pa4ok.demoexam;

public class Tiger extends EntityLiving implements ISoundEntity
{
    public Tiger(String type, int age) {
        super(type, age);
    }

    @Override
    public String getSound() {
        return "рррррр";
    }

    @Override
    public String toString() {
        return "Tiger{" +
                "type='" + type + '\'' +
                ", age=" + age +
                '}';
    }
}
