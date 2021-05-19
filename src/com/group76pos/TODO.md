# TODO

- [x] Memento.
  Saves and loads an object’s state.
  Fields: state
  Methods: saveToFile(path), loadFromFile(path)

- [x] IMemento
  Interface to be implemented by managers to allow them to save their state to a
  memento.
  Methods: save(), restore(memento)

- [x] Transaction
  Global list of sales made by the company regardless of by whom.
  Fields: product, time, amount, quantity

- [x] Sale
  Manages each sale made consisting of multiple transactions which can then be paid
  for by a customer.
  Fields: transactions, total, timeCompleted
  Methods: calculateTotal(), cancel(), checkout(), removeTransaction(), addTransaction()

- [x] SalesManager
  Keeps track of all sales made by the business and generates reports.
  Fields: instance, sales
  Methods: getInstance(), generateReport(groupBy), addSale(), issueReceipt(), save(), restore(memento)

- [x] Customer
  Fields: name, phoneNumber, email

- [x] BankAccount
  Fields: customer, accountNumber, bankName, balance, PIN
  Methods: verifyPin(pin), updateBalance(newBalance)

- [x] BankAccountManager
  Fields: instance, bankAccounts
  Methods: getInstance(), getBankAccount(accountNumber), save(), restore(memento)

- [x] Product
  Fields: ID, description, stockQuantity, purchasePrice, sellingPrice, reorderLevel
  Methods: updateStock(±quantity)

- [x] Burger
  Fields: hasLettuce, hasCheese

- [x] Fries
  Fields: size

- [x] Drink
  Fields: size, isBottle

- [x] StockManager
  Fields: instance, products
  Methods: getInstance(), checkReorderLevels(product), showAlert(message), save(), restore(memento)

- [x] StateManager
  Saves and loads the application’s state to and from a file.
  Fields: instance
  Methods: getInstance(), save(), restore()

- [x] ReportGroupBy

- [x] ProductSize
