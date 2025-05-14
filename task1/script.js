const expensesTable = document.getElementById('expenses-table');
const expensesBody = document.getElementById('expenses-body');
const addExpenseBtn = document.getElementById('add-expense-btn');
const calculateBtn = document.getElementById('calculate-btn');
const resultsDiv = document.getElementById('results');
const totalExpensesP = document.getElementById('total-expenses');
const averageDailyExpenseP = document.getElementById('average-daily-expense');
const topExpensesUL = document.getElementById('top-expenses');

let expenses = [];

addExpenseBtn.addEventListener('click', addExpense);
calculateBtn.addEventListener('click', calculate);

function addExpense() {
   const categoryInput = prompt('Enter category:');
   const amountInput = prompt('Enter amount ($):');
   const expense = { category: categoryInput, amount: parseFloat(amountInput) };
   expenses.push(expense);
   renderExpensesTable();
}

function renderExpensesTable() {
   expensesBody.innerHTML = '';
   expenses.forEach((expense, index) => {
      const row = document.createElement('tr');
      const categoryCell = document.createElement('td');
      const amountCell = document.createElement('td');
      categoryCell.textContent = expense.category;
      amountCell.textContent = `$${expense.amount}`;
      row.appendChild(categoryCell);
      row.appendChild(amountCell);
      expensesBody.appendChild(row);
   });
}

function calculate() {
   const totalExpenses = expenses.reduce((total, expense) => total + expense.amount, 0);
   const averageDailyExpense = totalExpenses / 30;
   const topExpenses = expenses.sort((a, b) => b.amount - a.amount).slice(0, 3);

   totalExpensesP.textContent = `Total expenses: $${totalExpenses}`;
   averageDailyExpenseP.textContent = `Average daily expense: $${averageDailyExpense.toFixed(2)}`;
   topExpensesUL.innerHTML = '';
   topExpenses.forEach((expense) => {
      const li = document.createElement('li');
      li.textContent = `${expense.category}: $${expense.amount}`;
      topExpensesUL.appendChild(li);
   });
   resultsDiv.style.display = 'block';
}
