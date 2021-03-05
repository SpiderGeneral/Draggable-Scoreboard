package spiderclient.mods.impl;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import spiderclient.gui.hud.ScreenPosition;
import spiderclient.mods.ModDraggable;

public class ModScoreboard extends ModDraggable{
	
	private int listHeight;
	private int listWidth;

	@Override
	public int getWidth() {
		return listWidth;
	}

	@Override
	public int getHeight() {
		return listHeight;
	}

	
	@Override
	public void render(ScreenPosition pos) {
		
		
		// seems to need this IF statement or it crashes with a NPE
		if(this.mc.theWorld.getScoreboard() != null) {
		   
		Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
		ScoreObjective scoreobjective = null;
		ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getCommandSenderName());
		

		ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();

        if (scoreplayerteam != null)
        {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0)
            {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        if (scoreobjective1 != null)
        {
            this.renderScoreboard(scoreobjective1, scaledresolution);
        }
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, (float)(j - 48), 0.0F);
        
        
        
        GlStateManager.popMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
        
   		
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
		}
	}
		
	// doesn't seem to work with this method in GuiIngame so i just transferred it here
	public void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution scaledResolution)
    {
        Scoreboard scoreboard = p_180475_1_.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(p_180475_1_);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
        {
            public boolean apply(Score p_apply_1_)
            {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15)
        {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else
        {
            collection = list;
        }

        int i = this.getFontRenderer().getStringWidth(p_180475_1_.getDisplayName());

        for (Score score : collection)
        {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, this.getFontRenderer().getStringWidth(s));
        }

        int i1 = collection.size() * this.getFontRenderer().FONT_HEIGHT;
        int j1 = pos.getAbsoluteY() + i1 + 10;
        int k1 = 3;
        int l1 = pos.getAbsoluteX() - i + i - k1 + 5;
        int j = 0;

        for (Score score1 : collection)
        {
        	++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            int k = j1 - j * this.getFontRenderer().FONT_HEIGHT;
            int l = pos.getAbsoluteX() - k1 + 2 + i;
            Gui.drawRect(l1 - 2, k, l, k + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString(s1, l1, k, 553648127);
            this.getFontRenderer().drawString(s2, l - this.getFontRenderer().getStringWidth(s2), k, 553648127);
            
            listHeight = i1 + 10;
            listWidth = (l) - (l1 - 2);

            if (j == collection.size())
            {
                String s3 = p_180475_1_.getDisplayName();
                Gui.drawRect(l1 - 2 , k - this.getFontRenderer().FONT_HEIGHT - 1, l, k - 1, 1610612736);
                Gui.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                this.getFontRenderer().drawString(s3, l1 + i / 2 - this.getFontRenderer().getStringWidth(s3) / 2, k - this.getFontRenderer().FONT_HEIGHT, 553648127);
            }
            
        }
    }
    private FontRenderer getFontRenderer()
    {
        return this.mc.fontRendererObj;
    }
}
