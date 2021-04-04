package gamefiles.items;

public abstract class Consumable extends Item {
    private long duration;
    private long durationTimer;
    private boolean consumed;

    public Consumable(int id, String name, String description, int quantity, boolean active, long duration) {
        super(id, name, description, quantity, active);
        this.duration = duration;
        this.durationTimer = 0;
        this.consumed = false;
    }

    public void use() {
        if (!this.isConsumed()) {
            durationTimer = this.getDuration();
            this.toggleConsumed();
        }
    }

    public boolean update() {
        if (this.durationTimer == 0) {
            this.toggleConsumed();
            this.toggleActive();
            this.subtractQuantity(1);
            return false; // ended; "no update"
        } else {
            durationTimer--;
            return true; // not ended; "updated"
        }
    }

    public long getDuration() {
        return this.duration;
    }

    public long getDurationTimer() {
        return this.durationTimer;
    }

    public void toggleConsumed() {
        if (this.consumed) {
            this.consumed = false;
        } else {
            this.consumed = true;
        }
    }

    public boolean isConsumed() {
        return this.consumed;
    }
}
