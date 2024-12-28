let num = Math.floor(Math.random() * 100) + 1; // Random number between 1 and 100
let chance = 10; // Total chances

// Initialize the number of lives left on the page
document.getElementById('n').innerHTML = chance;
document.getElementById('hint').innerHTML = "Guess";
document.getElementById('reset').addEventListener('click', resetGame);
document.getElementById('submit').addEventListener('click', () => {
    // Get the value from the input box
    const guess = document.getElementById('guess').value.trim();

    // Check if the user wants to quit
    if (guess.toLowerCase() === "quit") {
        console.log("You quit the game.");
        console.log(`Chances left: ${chance}`);
        return;
    }

    // Parse the guess to a number
    const guessNumber = parseInt(guess, 10);

    // Validate the input
    if (isNaN(guessNumber)) {
        console.log("Please enter a valid number.");
        return;
    }

    // Decrease the chances
    chance--;

    // Update the number of lives left on the page
    document.getElementById('n').innerHTML = chance;
    

    // Compare the guess with the random number
    if (guessNumber > num) {
        console.log("Guess smaller.");
        document.getElementById('hint').innerHTML = "Smaller";
    } else if (guessNumber < num) {
        console.log("Guess bigger.");
        document.getElementById('hint').innerHTML = "Bigger";
    } else {
        console.log("Correct guess! You win!");
        console.log(`The number was: ${num}`);
        document.getElementById('hint').innerHTML = `Correct!! It is${num}`;
        resetGame(); // Restart the game
        return;
    }

    // Check if chances are exhausted
    if (chance === 0) {
        console.log("Game over. You ran out of chances.");
        console.log(`The correct number was: ${num}`);
        document.getElementById('hint').innerHTML = "Out of Lives";
        resetGame(); // Restart the game
    }
});

// Function to reset the game
function resetGame() {
    num = Math.floor(Math.random() * 100) + 1; // Generate a new random number
    chance = 10; // Reset chances
    document.getElementById('n').innerHTML = chance; // Reset lives display
    document.getElementById('guess').value = ''; // Clear the input field
    console.log("Game restarted!");
}
