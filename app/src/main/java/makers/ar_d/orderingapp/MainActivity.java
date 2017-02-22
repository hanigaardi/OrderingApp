package makers.ar_d.orderingapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private CheckBox creamCheckBox;
    private CheckBox chocolateCheckBox;
    private TextView totalPriceTextView;
    private TextView quantityTextView;
    private Button orderButton;
    private Button resetButton;

    private static final int priceOfMilk = 5000;
    private int quantityOfMilk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        creamCheckBox = (CheckBox) findViewById(R.id.creamCheckBox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        quantityTextView = (TextView) findViewById(R.id.quantityMilkTextView);
        totalPriceTextView = (TextView) findViewById(R.id.orderSumarryTextView);
        orderButton = (Button) findViewById(R.id.orderButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        setEnabledReset();
    }

    /*
        Quantity order
     */
    public void incrementOrder(View view) {
        if (quantityOfMilk < 20) {
            //increment quantity of order milk
            quantityOfMilk += 1;
            //displaying a quantity of order milk
            Log.d("incrementOrder", "" + quantityOfMilk);
        } else {
            Log.d("incrementOrder", "quantity lebih dari 20");
        }

        quantityTextView.setText(String.valueOf(quantityOfMilk));
        setEnabledReset();
    }

    public void decrementOrder(View view) {
        if (quantityOfMilk > 0) {
            //increment quantity of order milk
            quantityOfMilk -= 1;
            //displaying a quantity of order milk
            Log.d("decrementOrder", "" + quantityOfMilk);
        } else {
            Log.d("decrementOrder", "quantity kurang dari 0");
        }

        quantityTextView.setText(String.valueOf(quantityOfMilk));
        setEnabledReset();
    }

    /*
        onClick Order show summary
     */
    public void showTotalPrice(View view) {
        displayOrderSummary();
    }

    public int calculateTotalPrice(int numberOfMilk, int milkPrice) {
        return numberOfMilk * milkPrice;
    }

    public void displayOrderSummary() {
        boolean hasWhippedCream = creamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int totalPrice = calculateTotalPrice(quantityOfMilk, priceOfMilk);
        String orderSummary = createOrderSummary(totalPrice, hasWhippedCream, hasChocolate);

        totalPriceTextView.setText(orderSummary);
        sendEmail(orderSummary);

        /*String orderSumaries = String.format(getString(R.string.order_summaries),
                quantityOfMilk, priceOfMilk, totalPrice);
        totalPriceTextView.setText(orderSumaries);*/

    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants whipped cream topping
     * @param priceOfMilk     of the order
     * @return text summary
     */

    private String createOrderSummary(int priceOfMilk, boolean addWhippedCream, boolean addChocolate) {
        String orderSummary = "Name: " + nameEditText.getText().toString();
        orderSummary += "\nAdd whipped cream? " + addWhippedCream;
        orderSummary += "\nAdd chocolate? " + addChocolate;
        orderSummary += "\nQuantity: " + quantityOfMilk;
        orderSummary += "\nTotal: Rp " + priceOfMilk;
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    void sendEmail(String orderSummary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        // only email apps should handle this
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "haha@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + nameEditText.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        startActivity(Intent.createChooser(intent, "Send Email"));

    }

    /*
        reset all item
     */

    public void reset(View view) {
        quantityOfMilk = 0;

        quantityTextView.setText("0");
        totalPriceTextView.setText("0");
        setEnabledReset();
    }

    /*
        setEnabled button Reset and Order
     */
    void setEnabledReset() {
        if (quantityOfMilk == 0) {
            resetButton.setEnabled(false);
            orderButton.setEnabled(false);
        } else {
            resetButton.setEnabled(true);
            orderButton.setEnabled(true);
        }
    }

}
