import yfinance as yf

# Define the stock symbol and period
symbol = "AAPL"  # Apple Inc. as an example
period = "1y"    # 1 year of data

# Fetch historical data
stock_data = yf.download(symbol, period=period)

# Function to determine gain/loss
def gain_or_loss(open_price, close_price):
    change_percent = (close_price - open_price) / open_price * 100
    if change_percent >= 1:
        return 'gain'
    elif change_percent <= -1:
        return 'loss'
    else:
        return 'neutral'

# Experiment parameters
n = 10  # Number of experiments

# List to track the number of days for each experiment
day_counts = []

for i in range(n):
    gains = 0
    losses = 0
    day_count = 0
    
    for index, row in stock_data.iterrows():
        open_price = row['Open']
        close_price = row['Close']
        
        result = gain_or_loss(open_price, close_price)
        day_count += 1
        
        if result == 'gain':
            gains += 1
        elif result == 'loss':
            losses += 1
        
        if gains > losses:
            break
    
    day_counts.append(day_count)
    print(f"Experiment {i+1}: {day_count} days")

print("\nTotal day counts in each experiment:", day_counts)
