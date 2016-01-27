//
//  AppDelegate.swift
//  CookieCrunch
//
//  Created by 20040167 on 13/02/2015.
//  Copyright (c) 2015 20040167. All rights reserved.
//

import UIKit
import SpriteKit
import AVFoundation

class GameViewController: UIViewController {
    // The scene draws the tiles and cookie sprites, and handles swipes.
    var scene: GameScene!
    
    var level: Level!
    
    var movesLeft = 0
    var score = 0
    
    @IBOutlet weak var targetLabel: UILabel!
    @IBOutlet weak var movesLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var gameOverPanel: UIImageView!
    @IBOutlet weak var shuffleButton: UIButton!
    
    var tapGestureRecognizer: UITapGestureRecognizer!
    
    lazy var backgroundMusic: AVAudioPlayer = {
        let url = NSBundle.mainBundle().URLForResource("ChipTuneCrunch", withExtension: "mp3")
        let player = AVAudioPlayer(contentsOfURL: url, error: nil)
        player.numberOfLoops = -1
        return player
        }()
    
    override func prefersStatusBarHidden() -> Bool {
        return true
    }
    
    override func shouldAutorotate() -> Bool {
        return true
    }
    
    override func supportedInterfaceOrientations() -> Int {
        return Int(UIInterfaceOrientationMask.AllButUpsideDown.rawValue)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Configure the view.
        let skView = view as SKView
        skView.multipleTouchEnabled = false
        
        // Create and configure the scene.
        scene = GameScene(size: skView.bounds.size)
        scene.scaleMode = .AspectFill
        
        // Load the level.
        level = Level(filename: "Level_1")
        scene.level = level
        scene.addTiles()
        scene.swipeHandler = handleSwipe
        
        // Hide the game over panel from the screen.
        gameOverPanel.hidden = true
        shuffleButton.hidden = true
        
        // Present the scene.
        skView.presentScene(scene)
        
        // Load and start background music.
        backgroundMusic.play()
        
        // Let's start the game!
        beginGame()
    }
    
    func beginGame() {
        movesLeft = level.maximumMoves
        score = 0
        updateLabels()
        
        level.resetComboMultiplier()
        
        scene.animateBeginGame() {
            self.shuffleButton.hidden = false
        }
        
        shuffle()
    }
    
    func shuffle() {
        // Delete the old cookie sprites, but not the tiles.
        scene.removeAllCookieSprites()
        
        // Fill up the level with new cookies, and create sprites for them.
        let newCookies = level.shuffle()
        scene.addSpritesForCookies(newCookies)
    }
    func handleSwipe(swap: Swap) {
        view.userInteractionEnabled = false
        
        if level.isPossibleSwap(swap) {
            level.performSwap(swap)
            scene.animateSwap(swap, completion: handleMatches)
        } else {
            scene.animateInvalidSwap(swap) {
                self.view.userInteractionEnabled = true
            }
        }
    }
    
    
    func handleMatches() {
        // Detect if there are any matches left.
        let chains = level.removeMatches()
        
        // If there are no more matches, then the player gets to move again.
        if chains.count == 0 {
            beginNextTurn()
            return
        }
        
        // First, remove any matches...
        scene.animateMatchedCookies(chains) {
            
            // Add the new scores to the total.
            for chain in chains {
                self.score += chain.score
            }
            self.updateLabels()
            
            // ...then shift down any cookies that have a hole below them...
            let columns = self.level.fillHoles()
            self.scene.animateFallingCookies(columns) {
                
                // ...and finally, add new cookies at the top.
                let columns = self.level.topUpCookies()
                self.scene.animateNewCookies(columns) {
                    
                    // Keep repeating this cycle until there are no more matches.
                    self.handleMatches()
                }
            }
        }
    }
    
    func beginNextTurn() {
        level.resetComboMultiplier()
        level.detectPossibleSwaps()
        view.userInteractionEnabled = true
        decrementMoves()
    }
    
    func updateLabels() {
        targetLabel.text = NSString(format: "%ld", level.targetScore)
        movesLabel.text = NSString(format: "%ld", movesLeft)
        scoreLabel.text = NSString(format: "%ld", score)
    }
    
    func decrementMoves() {
        --movesLeft
        updateLabels()
        
        if score >= level.targetScore {
            gameOverPanel.image = UIImage(named: "LevelComplete")
            showGameOver()
        } else if movesLeft == 0 {
            gameOverPanel.image = UIImage(named: "GameOver")
            showGameOver()
        }
    }
    
    func showGameOver() {
        gameOverPanel.hidden = false
        scene.userInteractionEnabled = false
        shuffleButton.hidden = true
        
        scene.animateGameOver() {
            self.tapGestureRecognizer = UITapGestureRecognizer(target: self, action: "hideGameOver")
            self.view.addGestureRecognizer(self.tapGestureRecognizer)
        }
    }
    
    func hideGameOver() {
        view.removeGestureRecognizer(tapGestureRecognizer)
        tapGestureRecognizer = nil
        
        gameOverPanel.hidden = true
        scene.userInteractionEnabled = true
        
        beginGame()
    }
    
    @IBAction func shuffleButtonPressed(AnyObject) {
        shuffle()
        
        // Pressing the shuffle button costs a move.
        decrementMoves()
    }
}
