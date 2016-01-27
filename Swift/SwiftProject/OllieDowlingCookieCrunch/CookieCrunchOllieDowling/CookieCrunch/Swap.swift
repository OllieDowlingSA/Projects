//
//  AppDelegate.swift
//  CookieCrunch
//
//  Created by 20040167 on 13/02/2015.
//  Copyright (c) 2015 20040167. All rights reserved.
//


struct Swap: Printable, Hashable {
  let cookieA: Cookie
  let cookieB: Cookie

  init(cookieA: Cookie, cookieB: Cookie) {
    self.cookieA = cookieA
    self.cookieB = cookieB
  }

  var description: String {
    return "swap \(cookieA) with \(cookieB)"
  }

  var hashValue: Int {
    return cookieA.hashValue ^ cookieB.hashValue
  }
}

func ==(lhs: Swap, rhs: Swap) -> Bool {
  return (lhs.cookieA == rhs.cookieA && lhs.cookieB == rhs.cookieB) ||
         (lhs.cookieB == rhs.cookieA && lhs.cookieA == rhs.cookieB)
}
