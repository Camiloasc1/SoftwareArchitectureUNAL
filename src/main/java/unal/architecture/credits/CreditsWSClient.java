package unal.architecture.credits;

public class CreditsWSClient {

    public static String createCreditWS(String name, float quantity, int numberOfPayments) {
        System.out.println(name + "  " + quantity + " " + numberOfPayments);
        MakeAcquiredProductWS_Service service = new MakeAcquiredProductWS_Service();
        MakeAcquiredProductWS creditsService = service.getMakeAcquiredProductWSPort();
        String a;

        a = creditsService.makeAcquiredProduct(name,quantity,numberOfPayments);
        System.out.println(a);
        return a;
    }

}
