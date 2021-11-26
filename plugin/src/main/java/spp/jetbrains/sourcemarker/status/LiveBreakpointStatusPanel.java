package spp.jetbrains.sourcemarker.status;

import com.codahale.metrics.Meter;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;
import spp.protocol.utils.TimeUtilsKt;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveBreakpointStatusPanel extends JPanel {

    private final Meter meter = new Meter();
    private int hitLimit;

    public LiveBreakpointStatusPanel() {
        initComponents();
    }

    public void setHitLimit(int hitLimit) {
        this.hitLimit = hitLimit;
    }

    public void setStatus(String status, Color statusColor) {
        statusValueLabel.setText(status);
        statusValueLabel.setForeground(statusColor);

        if ("Error".equals(status) || "Complete".equals(status)) {
            expiresLabel.setVisible(false);
            expiresValueLabel.setVisible(false);

            if (hitLimit == 1) {
                rateLabel.setVisible(false);
                rateValueLabel.setVisible(false);
            }
        }
    }

    public void incrementHits() {
        meter.mark();
        setHits(meter.getCount());
        setRate(meter.getMeanRate());
    }

    private void setHits(long hits) {
        hitsValueLabel.setText(Long.toString(hits));
    }

    private void setRate(double rate) {
        rateValueLabel.setText(TimeUtilsKt.fromPerSecondToPrettyFrequency(rate));
    }

    public void setExpires(long expiresAt) {
        updateExpiresLabel(expiresAt);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (expiresValueLabel.isVisible()) {
                updateExpiresLabel(expiresAt);
            } else {
                executor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);

        new Thread(() -> {
            try {
                Thread.sleep(expiresAt - System.currentTimeMillis());
            } catch (InterruptedException ignored) {
            }
            executor.shutdown();
        }).start();
    }

    private void updateExpiresLabel(long expiresTime) {
        long diffMs = expiresTime - System.currentTimeMillis();
        long diffSec = diffMs / 1000;
        long min = diffSec / 60;
        long sec = diffSec % 60;
        if (min > 0) {
            expiresValueLabel.setForeground(Color.decode("#BBBBBB"));
            expiresValueLabel.setText(min + "min " + sec + "s");
        } else {
            expiresValueLabel.setForeground(Color.decode("#e1483b"));
            expiresValueLabel.setText(sec + "s");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        panel1 = new JPanel();
        statusLabel = new JLabel();
        statusValueLabel = new JLabel();
        separator1 = new JSeparator();
        hitsLabel = new JLabel();
        hitsValueLabel = new JLabel();
        rateLabel = new JLabel();
        rateValueLabel = new JLabel();
        expiresLabel = new JLabel();
        expiresValueLabel = new JLabel();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setBackground(new Color(43, 43, 43));
        //setBorder(new LineBorder(new Color(85, 85, 85)));
        setFont(new Font("Roboto Light", Font.PLAIN, 15));
        setLayout(new FormLayout(
            "default:grow",
            "fill:default:grow"));

        //======== panel1 ========
        {
            panel1.setBackground(null);
            panel1.setFont(new Font("Roboto Light", Font.PLAIN, 15));
            panel1.setLayout(new FormLayout(
                new ColumnSpec[] {
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    new ColumnSpec(ColumnSpec.CENTER, Sizes.DEFAULT, FormSpec.NO_GROW),
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC
                },
                RowSpec.decodeSpecs("fill:default:grow")));

            //---- statusLabel ----
            statusLabel.setText("Status");
            statusLabel.setFont(new Font("Roboto Light", Font.PLAIN, 15));
            statusLabel.setForeground(Color.gray);
            panel1.add(statusLabel, cc.xy(1, 1));

            //---- statusValueLabel ----
            statusValueLabel.setText("Active");
            statusValueLabel.setFont(new Font("Roboto Light", Font.PLAIN, 16));
            statusValueLabel.setForeground(new Color(106, 135, 89));
            panel1.add(statusValueLabel, cc.xy(3, 1));

            //---- separator1 ----
            separator1.setOrientation(SwingConstants.VERTICAL);
            separator1.setMinimumSize(new Dimension(2, 1));
            separator1.setMaximumSize(new Dimension(2, 1));
            separator1.setPreferredSize(new Dimension(2, 1));
            separator1.setForeground(Color.darkGray);
            panel1.add(separator1, new CellConstraints(5, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(5, 0, 5, 0)));

            //---- hitsLabel ----
            hitsLabel.setText("Hits");
            hitsLabel.setForeground(Color.gray);
            hitsLabel.setFont(new Font("Roboto Light", Font.PLAIN, 15));
            panel1.add(hitsLabel, cc.xy(7, 1));

            //---- hitsValueLabel ----
            hitsValueLabel.setText("n/a");
            hitsValueLabel.setFont(new Font("Roboto Light", Font.PLAIN, 16));
            panel1.add(hitsValueLabel, cc.xy(9, 1));

            //---- rateLabel ----
            rateLabel.setText("Rate");
            rateLabel.setForeground(Color.gray);
            rateLabel.setFont(new Font("Roboto Light", Font.PLAIN, 15));
            panel1.add(rateLabel, cc.xy(11, 1));

            //---- rateValueLabel ----
            rateValueLabel.setText("n/a");
            rateValueLabel.setFont(new Font("Roboto Light", Font.PLAIN, 16));
            panel1.add(rateValueLabel, cc.xy(13, 1));

            //---- expiresLabel ----
            expiresLabel.setText("Expires");
            expiresLabel.setForeground(Color.gray);
            expiresLabel.setFont(new Font("Roboto Light", Font.PLAIN, 15));
            panel1.add(expiresLabel, cc.xy(15, 1));

            //---- expiresValueLabel ----
            expiresValueLabel.setText("n/a");
            expiresValueLabel.setFont(new Font("Roboto Light", Font.PLAIN, 16));
            panel1.add(expiresValueLabel, cc.xy(17, 1));
        }
        add(panel1, new CellConstraints(1, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(2, 4, 2, 2)));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel1;
    private JLabel statusLabel;
    private JLabel statusValueLabel;
    private JSeparator separator1;
    private JLabel hitsLabel;
    private JLabel hitsValueLabel;
    private JLabel rateLabel;
    private JLabel rateValueLabel;
    private JLabel expiresLabel;
    private JLabel expiresValueLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}