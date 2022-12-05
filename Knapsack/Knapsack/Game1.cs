using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using System.Collections.Generic;
using System.Collections;
using System.IO;
using System;

namespace Knapsack
{
    public class Game1 : Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;
        Texture2D backgroundTexture;
        Rectangle buttonPos;
        Rectangle knapsackPos;
        Rectangle knapsack2Pos;
        Random random;

        SpriteFont font;

        Knapsack firstKnapsack;
        Knapsack secondKnapsack;

        Item item0;
        Item item1;
        Item item2;
        Item item3;
        Item item4;
        Item item5;
        Item item6;
        Item item7;
        Item item8;
        Item item9;

        List<Item> itemList = new List<Item>();

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
            graphics.PreferredBackBufferHeight = 900;
            graphics.PreferredBackBufferWidth = 1080;
        }

        protected override void Initialize()
        {
            base.Initialize();
            IsMouseVisible = true;
        }

        protected override void LoadContent()
        {
            spriteBatch = new SpriteBatch(GraphicsDevice);
            backgroundTexture = Content.Load<Texture2D>("walltile");
            buttonPos = new Rectangle(0, 0, Window.ClientBounds.Width / 3, Window.ClientBounds.Height);
            knapsackPos = new Rectangle(Window.ClientBounds.Width / 3, 0, (Window.ClientBounds.Width / 3) * 2, Window.ClientBounds.Height / 2);
            knapsack2Pos = new Rectangle(Window.ClientBounds.Width / 3, Window.ClientBounds.Height / 2, (Window.ClientBounds.Width / 3) * 2, Window.ClientBounds.Height / 2);
            font = Content.Load<SpriteFont>("font");
            
            random = new Random();

            //////////////////////////////////////////////////////////////////////////

            firstKnapsack = new Knapsack(1, 70);
            secondKnapsack = new Knapsack(2, 65);

            /////////////////////////////////////////////////////////////////////////


            //for (int i = 0; i < 20; i++)
            //{
            //    Item item = new Item(random.Next(1, 10), random.Next(1, 20), "randomItem" + i);
            //    itemList.Add(item);
            //}

            item0 = new Item(1, 1, "apple");
            itemList.Add(item0);
            item1 = new Item(3, 6, "book");
            itemList.Add(item1);
            item2 = new Item(2, 8, "gloves");
            itemList.Add(item2);
            item3 = new Item(10, 1, "stone");
            itemList.Add(item3);
            item4 = new Item(6, 5, "hat");
            itemList.Add(item4);
            item5 = new Item(5, 6, "stick");
            itemList.Add(item5);
            item6 = new Item(1, 4, "needle");
            itemList.Add(item6);
            item7 = new Item(1, 2, "jarn");
            itemList.Add(item7);
            item8 = new Item(1, 10, "ring");
            itemList.Add(item8);
            item9 = new Item(1, 0, "paper");
            itemList.Add(item9);

        }


        protected override void UnloadContent()
        {
        }

        protected override void Update(GameTime gameTime)
        {
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed || Keyboard.GetState().IsKeyDown(Keys.Escape))
                Exit();
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.Black);
            spriteBatch.Begin();

            spriteBatch.Draw(backgroundTexture, buttonPos, Color.White);
            spriteBatch.Draw(backgroundTexture, knapsackPos, Color.SandyBrown);
            spriteBatch.Draw(backgroundTexture, knapsack2Pos, Color.SandyBrown);

            spriteBatch.DrawString(font, "Information ", new Vector2(buttonPos.Center.X, buttonPos.Center.Y), Color.Orange);
            spriteBatch.DrawString(font, "First Knapsack ", new Vector2(knapsackPos.Center.X, knapsackPos.Center.Y), Color.Orange);
            spriteBatch.DrawString(font, "Second Knapsack ", new Vector2(knapsack2Pos.Center.X, knapsack2Pos.Center.Y), Color.Orange);

            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
