package fondt2.tlc;

public class PhonePlan {
	String name;
	Rate[] rates;
	
	public PhonePlan(String name, Rate[] rates) {
		this.name = name;
		this.rates = rates;
	}
	
	public String getName() {
		return name;
	}
	
	public Rate getRate(PhoneCall phoneCall) {
		for(int i = 0; i < rates.length; i++) {
			if(rates[i].isApplicableTo(phoneCall.getDestNumber())) {
				return rates[i];
			}
		}
		
		return null;
	}
	
	public double getCallCost(PhoneCall call) {
		Rate currentRate = getRate(call);
		if(currentRate == null) return -1;
		else return currentRate.getCallCost(call);
	}
	
	public boolean isValid() {
		for(int i = 0; i < rates.length; i++) {
			if(!rates[i].isValid()) {
				return false;
			}
		}
		
		return true;
	}
}
