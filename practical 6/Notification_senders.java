interface Notifier
{
    void send(String message);
}

interface Urgent {}

class Email implements Notifier
 {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

// SMS notifier marked urgent
class SMS implements Notifier, Urgent 
{
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class Notification_senders
{
    public static void main(String[] arg)
    {
        Notifier Email = (msg)->System.out.println("SendingEmail:"+msg);
       
        Notifier SMS =new Notifier()
        {
            @Override
            public void send(String msg)
            {
                System.out.println("Snding SMS:"+msg);
            }
        };

        Notifier UrgentNotifier = new Notifier()
        {
            @Override
            public void send (String msg)
            {
                System.out.println("Sending SMS:"+msg);
            }
        };

        Urgent UrgentMarker =new Urgent(){};

        Notifier[] Notifications ={ Email, SMS};

        String message ="Exam starts Tomorrow";

        for(Notifier N: Notifications)
        {
            N.send(message);

            if(N instanceof Urgent)
            {
                N.send(message);
            }
        }
    }
}