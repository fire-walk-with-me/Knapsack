using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace PathFinding
{
    static class KeyMouseReader
    {
        public static KeyboardState keyState, oldKeyState = Keyboard.GetState();
        public static MouseState mouseState, oldMouseState = Mouse.GetState();

        public static bool KeyPressed(Keys key)
        {
            return keyState.IsKeyDown(key) && oldKeyState.IsKeyUp(key);
        }
        public static bool KeyHold(Keys key)
        {
            return keyState.IsKeyDown(key);
        }
        public static bool LeftClick()
        {
            return mouseState.LeftButton == ButtonState.Pressed && oldMouseState.LeftButton == ButtonState.Released;
        }

        public static bool LeftHold()
        {
            return mouseState.LeftButton == ButtonState.Pressed;
        }

        public static bool RightClick()
        {
            return mouseState.RightButton == ButtonState.Pressed && oldMouseState.RightButton == ButtonState.Released;
        }

        //public static Vector2 MouseWorldPosition => new Vector2(EntityManager.Player.Position.X + 20, EntityManager.Player.Position.Y + 20) + mouseState.Position.ToVector2() - new Vector2(Game1.camera.Viewport.Width / 2, Game1.camera.Viewport.Height / 2);

        public static Vector2 MouseScreenPosition => mouseState.Position.ToVector2();


        //Should be called at beginning of Update in Game
        public static void Update()
        {
            oldKeyState = keyState;
            keyState = Keyboard.GetState();
            oldMouseState = mouseState;
            mouseState = Mouse.GetState();
        }
    }
}