package gringottbank.model;

public class GringottAtm implements Atm {

	public CoinAmount normalize(CoinAmount amount) {
		int galleons = amount.getQuantity(Coin.GALLEON);
		int sickles =  amount.getQuantity(Coin.SICKLE);
		int knuts =    amount.getQuantity(Coin.KNUT);
		while (knuts > Coin.SICKLE.getDivider()) { knuts -=Coin.SICKLE.getDivider(); sickles++; }
		while (sickles > Coin.GALLEON.getDivider()) { sickles -=Coin.GALLEON.getDivider(); galleons++; }
		return new CoinAmount().plus(Coin.GALLEON, galleons).plus(Coin.SICKLE,sickles).plus(Coin.KNUT,knuts);
	} 

	@Override
	public Amount withdraw(CoinAmount amount) throws ImpossibleWithdrawException {
		amount = normalize(amount);
		if(amount.isNegative()) throw new ImpossibleWithdrawException("Amount negativo");
		
		int moreSickles = amount.getQuantity(Coin.KNUT)/Coin.SICKLE.getDivider();
		amount = amount.plus(Coin.SICKLE, moreSickles).minus(Coin.KNUT, moreSickles*Coin.SICKLE.getDivider());
		
		int moreGalleons = (Math.max(0, amount.getQuantity(Coin.SICKLE)-5))/Coin.GALLEON.getDivider();
		amount = amount.plus(Coin.GALLEON, moreGalleons).minus(Coin.SICKLE, moreGalleons*Coin.GALLEON.getDivider());
		
		if(amount.getQuantity(Coin.SICKLE) < 5 && amount.getQuantity(Coin.GALLEON) > 0) {
			amount = amount.plus(Coin.SICKLE, Coin.GALLEON.getDivider()).minus(Coin.GALLEON, 1);
		}
		
		return amount;
	}

	@Override
	public Amount withdraw(CurrencyAmount amount) throws ImpossibleWithdrawException {
		if(amount.isNegative()) throw new ImpossibleWithdrawException("Amount negativo");
		
		double galleons = amount.getQuantity()/amount.getCurrency().getExchangeRate();
		int galleonsAmount = (int) Math.floor(galleons);
		
		double sickles = (galleons-galleonsAmount)*Coin.GALLEON.getDivider();
		
		if(sickles < 5 && galleonsAmount > 0) {
			galleonsAmount--;
			sickles += Coin.GALLEON.getDivider();
		}

		int sicklesAmount = (int) Math.floor(sickles);
		
		double knuts = (sickles-sicklesAmount)*Coin.SICKLE.getDivider();
		int knutsAmount = (int) Math.floor(knuts);
		
		return new CoinAmount().plus(Coin.GALLEON, galleonsAmount).plus(Coin.SICKLE, sicklesAmount).plus(Coin.KNUT, knutsAmount);
	}
	
}
