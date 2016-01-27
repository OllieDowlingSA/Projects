//
//  AppDelegate.swift
//  CookieCrunch
//
//  Created by 20040167 on 13/02/2015.
//  Copyright (c) 2015 20040167. All rights reserved.
//

import Foundation

let NumColumns = 9
let NumRows = 9

class Level {
    // The 2D array that keeps track of where the Cookies are.
    private var cookies = Array2D<Cookie>(columns: NumColumns, rows: NumRows)
    
    // The 2D array that contains the layout of the level.
    private var tiles = Array2D<Tile>(columns: NumColumns, rows: NumRows)
    
    private var possibleSwaps = Set<Swap>()
    
    let targetScore: Int!
    let maximumMoves: Int!
    
    
    private var comboMultiplier = 0
    
    // Create a level by loading it from a file.
    init(filename: String) {
        if let dictionary = Dictionary<String, AnyObject>.loadJSONFromBundle(filename) {
            
            if let tilesArray: AnyObject = dictionary["tiles"] {
                
                // Loop through the rows...
                for (row, rowArray) in enumerate(tilesArray as [[Int]]) {
                    
                    
                    let tileRow = NumRows - row - 1
                    
                    // Loop through the columns in the current row...
                    for (column, value) in enumerate(rowArray) {
                        
                        // If the value is 1, create a tile object.
                        if value == 1 {
                            tiles[column, tileRow] = Tile()
                        }
                    }
                    
                    targetScore = (dictionary["targetScore"] as NSNumber).integerValue
                    maximumMoves = (dictionary["moves"] as NSNumber).integerValue
                }
            }
        }
    }
    
    
    func shuffle() -> Set<Cookie> {
        var set: Set<Cookie>
        
        do {
            // Removes the old cookies and fills up the level with all new ones.
            set = createInitialCookies()
            
            detectPossibleSwaps()
            
            // If there are no possible moves, then keep trying again until there are.
        }
            while possibleSwaps.count == 0
        
        return set
    }
    
    private func createInitialCookies() -> Set<Cookie> {
        var set = Set<Cookie>()
        
        for row in 0..<NumRows {
            for column in 0..<NumColumns {
                
                // Only make a new cookie if there is a tile at this spot.
                if tiles[column, row] != nil {
                    
                    var cookieType: CookieType
                    do {
                        cookieType = CookieType.random()
                    }
                        while (column >= 2 &&
                            cookies[column - 1, row]?.cookieType == cookieType &&
                            cookies[column - 2, row]?.cookieType == cookieType)
                            || (row >= 2 &&
                                cookies[column, row - 1]?.cookieType == cookieType &&
                                cookies[column, row - 2]?.cookieType == cookieType)
                    
                    // Create a new cookie and add it to the 2D array.
                    let cookie = Cookie(column: column, row: row, cookieType: cookieType)
                    cookies[column, row] = cookie
                    
                    // Also add the cookie to the set so we can tell our caller about it.
                    set.addElement(cookie)
                }
            }
        }
        return set
    }
    
    func cookieAtColumn(column: Int, row: Int) -> Cookie? {
        assert(column >= 0 && column < NumColumns)
        assert(row >= 0 && row < NumRows)
        return cookies[column, row]
    }
    
    // Determines whether there's a tile at the specified column and row.
    func tileAtColumn(column: Int, row: Int) -> Tile? {
        assert(column >= 0 && column < NumColumns)
        assert(row >= 0 && row < NumRows)
        return tiles[column, row]
    }
    
    // Determines whether the suggested swap is a valid one, i.e. it results in at
    // least one new chain of 3 or more cookies of the same type.
    func isPossibleSwap(swap: Swap) -> Bool {
        return possibleSwaps.containsElement(swap)
    }
    
    func performSwap(swap: Swap) {
        // Need to make temporary copies of these because they get overwritten.
        let columnA = swap.cookieA.column
        let rowA = swap.cookieA.row
        let columnB = swap.cookieB.column
        let rowB = swap.cookieB.row
        
        cookies[columnA, rowA] = swap.cookieB
        swap.cookieB.column = columnA
        swap.cookieB.row = rowA
        
        cookies[columnB, rowB] = swap.cookieA
        swap.cookieA.column = columnB
        swap.cookieA.row = rowB
    }
    
    func detectPossibleSwaps() {
        var set = Set<Swap>()
        
        for row in 0..<NumRows {
            for column in 0..<NumColumns {
                if let cookie = cookies[column, row] {
                    
                    if column < NumColumns - 1 {
                        
                        if let other = cookies[column + 1, row] {
                            // Swap them
                            cookies[column, row] = other
                            cookies[column + 1, row] = cookie
                            
                            // Is either cookie now part of a chain?
                            if hasChainAtColumn(column + 1, row: row) ||
                                hasChainAtColumn(column, row: row) {
                                    set.addElement(Swap(cookieA: cookie, cookieB: other))
                            }
                            
                            // Swap them back
                            cookies[column, row] = cookie
                            cookies[column + 1, row] = other
                        }
                    }
                    
                    if row < NumRows - 1 {
                        
                        if let other = cookies[column, row + 1] {
                            // Swap them
                            cookies[column, row] = other
                            cookies[column, row + 1] = cookie
                            
                            // Is either cookie now part of a chain?
                            if hasChainAtColumn(column, row: row + 1) ||
                                hasChainAtColumn(column, row: row) {
                                    set.addElement(Swap(cookieA: cookie, cookieB: other))
                            }
                            
                            // Swap them back
                            cookies[column, row] = cookie
                            cookies[column, row + 1] = other
                        }
                    }
                }
            }
        }
        
        possibleSwaps = set
    }
    
    private func hasChainAtColumn(column: Int, row: Int) -> Bool {
        // Here we do ! because we know there is a cookie here
        let cookieType = cookies[column, row]!.cookieType
        
        var horzLength = 1
        for var i = column - 1; i >= 0 && cookies[i, row]?.cookieType == cookieType; --i, ++horzLength { }
        for var i = column + 1; i < NumColumns && cookies[i, row]?.cookieType == cookieType; ++i, ++horzLength { }
        if horzLength >= 3 { return true }
        
        var vertLength = 1
        for var i = row - 1; i >= 0 && cookies[column, i]?.cookieType == cookieType; --i, ++vertLength { }
        for var i = row + 1; i < NumRows && cookies[column, i]?.cookieType == cookieType; ++i, ++vertLength { }
        return vertLength >= 3
    }
    
    func removeMatches() -> Set<Chain> {
        let horizontalChains = detectHorizontalMatches()
        let verticalChains = detectVerticalMatches()
        
        
        removeCookies(horizontalChains)
        removeCookies(verticalChains)
        
        calculateScores(horizontalChains)
        calculateScores(verticalChains)
        
        return horizontalChains.unionSet(verticalChains)
    }
    
    private func detectHorizontalMatches() -> Set<Chain> {
        var set = Set<Chain>()
        
        for row in 0..<NumRows {
            
            for var column = 0; column < NumColumns - 2 ; {
                
                // If there is a cookie/tile at this position...
                if let cookie = cookies[column, row] {
                    let matchType = cookie.cookieType
                    
                    // And the next two columns have the same type...
                    if cookies[column + 1, row]?.cookieType == matchType &&
                        cookies[column + 2, row]?.cookieType == matchType {
                            
                            let chain = Chain(chainType: .Horizontal)
                            do {
                                chain.addCookie(cookies[column, row]!)
                                ++column
                            }
                                while column < NumColumns && cookies[column, row]?.cookieType == matchType
                            
                            set.addElement(chain)
                            continue
                    }
                }
                
                // Cookie did not match or empty tile, so skip over it.
                ++column
            }
        }
        return set
    }
    
    // Same as the horizontal version but steps through the array differently.
    private func detectVerticalMatches() -> Set<Chain> {
        var set = Set<Chain>()
        
        for column in 0..<NumColumns {
            for var row = 0; row < NumRows - 2; {
                if let cookie = cookies[column, row] {
                    let matchType = cookie.cookieType
                    
                    if cookies[column, row + 1]?.cookieType == matchType &&
                        cookies[column, row + 2]?.cookieType == matchType {
                            
                            let chain = Chain(chainType: .Vertical)
                            do {
                                chain.addCookie(cookies[column, row]!)
                                ++row
                            }
                                while row < NumRows && cookies[column, row]?.cookieType == matchType
                            
                            set.addElement(chain)
                            continue
                    }
                }
                ++row
            }
        }
        return set
    }
    
    private func removeCookies(chains: Set<Chain>) {
        for chain in chains {
            for cookie in chain.cookies {
                cookies[cookie.column, cookie.row] = nil
            }
        }
    }
    
    private func calculateScores(chains: Set<Chain>) {
        // 3-chain is 60 pts, 4-chain is 120, 5-chain is 180, and so on
        for chain in chains {
            chain.score = 60 * (chain.length - 2) * comboMultiplier
            ++comboMultiplier
        }
    }
    
    // Should be called at the start of every new turn.
    func resetComboMultiplier() {
        comboMultiplier = 1
    }
    
    
    func fillHoles() -> [[Cookie]] {
        var columns = [[Cookie]]()       // you can also write this Array<Array<Cookie>>
        
        for column in 0..<NumColumns {
            var array = [Cookie]()
            
            for row in 0..<NumRows {
                
                // If there is a tile at this position but no cookie, then there's a hole.
                if tiles[column, row] != nil && cookies[column, row] == nil {
                    
                    // Scan upward to find a cookie.
                    for lookup in (row + 1)..<NumRows {
                        if let cookie = cookies[column, lookup] {
                            // Swap that cookie with the hole.
                            cookies[column, lookup] = nil
                            cookies[column, row] = cookie
                            cookie.row = row
                            
                            array.append(cookie)
                            
                            // Don't need to scan up any further.
                            break
                        }
                    }
                }
            }
            
            if !array.isEmpty {
                columns.append(array)
            }
        }
        return columns
    }
    
    func topUpCookies() -> [[Cookie]] {
        var columns = [[Cookie]]()
        var cookieType: CookieType = .Unknown
        for column in 0..<NumColumns {
            var array = [Cookie]()
            
            
            for var row = NumRows - 1; row >= 0 && cookies[column, row] == nil; --row {
                
                // Found a hole?
                if tiles[column, row] != nil {
                    
                    var newCookieType: CookieType
                    do {
                        newCookieType = CookieType.random()
                    } while newCookieType == cookieType
                    cookieType = newCookieType
                    
                    // Create a new cookie and add it to the array for this column.
                    let cookie = Cookie(column: column, row: row, cookieType: cookieType)
                    cookies[column, row] = cookie
                    array.append(cookie)
                }
            }
            
            if !array.isEmpty {
                columns.append(array)
            }
        }
        return columns
    }
}
