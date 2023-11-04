package org.velezreyes.quiz.question6;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineImpl implements VendingMachine {

  private Map<String, Integer> drinks = new HashMap<>();
  private int quarters = 0;
  private static volatile VendingMachineImpl instance;

  private VendingMachineImpl() {
    drinks.put("ScottCola", 75);
    drinks.put("KarenTea", 100);
  }

  public static VendingMachineImpl getInstance() {
    if (instance == null) {
      synchronized (VendingMachineImpl.class) {
        if (instance == null) {
          instance = new VendingMachineImpl();
        }
      }
    }
    return instance;
  }

  public void insertQuarter() {
    quarters += 25;
  }

  @Override
  public Drink pressButton(String name) throws NotEnoughMoneyException, UnknownDrinkException {
    Integer drinkPrice = drinks.get(name);
    if (drinkPrice == null) {
      throw new UnknownDrinkException();
    }
    if (quarters < drinkPrice) {
      throw new NotEnoughMoneyException();
    }

    quarters -= drinkPrice;
    return deliverDrink(name);
  }

  private Drink deliverDrink(String name) {
    return new Drink() {
      @Override
      public String getName() {
        return name;
      }

      @Override
      public boolean isFizzy() {
        return "ScottCola".equals(name);
      }
    };
  }
}
