# Notes-App

## Collaboration Steps

1. Create a branch with branch name realted to either issue number or a descriptive name
```zsh
# <your-branch-name> - <intent>:<issue_number or descriptive_name>
git branch checkout -b <your-branch-name>
```
2. Add ONLY the necessay files by running -
```zsh
git add <file1> <file2> . . .
```
3. Push to staging with a concise commit message - 
```zsh
git commit -m "<your_message>"
```
4. Push to origin -
```zsh
git push origin <your-branch-name>
```