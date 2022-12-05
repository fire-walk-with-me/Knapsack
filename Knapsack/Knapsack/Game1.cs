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


            //for (int i = 0; i < 40; i++)
            //{
            //    Item item = new Item(random.Next(1, 10), random.Next(1, 20), "randomItem" + i);
            //    itemList.Add(item);
            //}

            item0 = new Item(1, 1, "Apple");
            itemList.Add(item0);
            item1 = new Item(3, 6, "Book");
            itemList.Add(item1);
            item2 = new Item(2, 8, "Gloves");
            itemList.Add(item2);
            item3 = new Item(10, 1, "Stone");
            itemList.Add(item3);
            item4 = new Item(6, 5, "Hat");
            itemList.Add(item4);
            item5 = new Item(5, 6, "Stick");
            itemList.Add(item5);
            item6 = new Item(1, 4, "Needle");
            itemList.Add(item6);
            item7 = new Item(1, 2, "Jarn");
            itemList.Add(item7);
            item8 = new Item(1, 10, "Ring");
            itemList.Add(item8);
            item9 = new Item(1, 1, "Paper");
            itemList.Add(item9);

            //////////////////////////////////////////////////////////////////////

            firstKnapsack.AddContent(item0);

            secondKnapsack.AddContent(item3);
            secondKnapsack.AddContent(item2);
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

            for (int i = 0; i < itemList.Count; i++)
            {
                spriteBatch.DrawString(font, "Item: " + itemList[i].itemName + " Value: " + itemList[i].value + " Weight: " + itemList[i].weight + " V/W ratio: " + itemList[i].weightValueRatio.ToString("0.00"),
                    new Vector2(buttonPos.X + 25, 50 + (buttonPos.Y + i * 20)), Color.Orange);
            }

            for (int i = 0; i < firstKnapsack.contentCount(); i++)
            {
                spriteBatch.DrawString(font, "Item: " + firstKnapsack.Item(i).itemName + " Value: " + firstKnapsack.Item(i).value + " Weight: " + firstKnapsack.Item(i).weight + " V/W ratio: " + firstKnapsack.Item(i).weightValueRatio.ToString("0.00"),
                    new Vector2(knapsackPos.X + 45, 30 + (knapsackPos.Y + i * 20)), Color.Orange);
            }

            for (int i = 0; i < secondKnapsack.contentCount(); i++)
            {
                spriteBatch.DrawString(font, "Item: " + secondKnapsack.Item(i).itemName + " Value: " + secondKnapsack.Item(i).value + " Weight: " + secondKnapsack.Item(i).weight + " V/W ratio: " + secondKnapsack.Item(i).weightValueRatio.ToString("0.00"),
                    new Vector2(knapsack2Pos.X + 45, 30 + (knapsack2Pos.Y + i * 20)), Color.Orange);
            }

            spriteBatch.DrawString(font, "Avalible Items", new Vector2(buttonPos.X + 25 , buttonPos.Y + 20), Color.Orange);
            spriteBatch.DrawString(font, "First Knapsack    Capacaty: " + firstKnapsack.capacity + "  Current Fill: " + firstKnapsack.currentFill + "  Current Value: " + firstKnapsack.currentValue, new Vector2(knapsackPos.X + 40, knapsackPos.Y), Color.Orange);
            spriteBatch.DrawString(font, "Second Knapsack    Capacaty: " + secondKnapsack.capacity + "  Current Fill: " + secondKnapsack.currentFill + "  Current Value: " + secondKnapsack.currentValue, new Vector2(knapsack2Pos.X + 40, knapsack2Pos.Y), Color.Orange);

            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
